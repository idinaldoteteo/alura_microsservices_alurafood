package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Fn;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.constructs.Construct;

import java.util.HashMap;
import java.util.Map;

public class AluraServiceStack extends Stack {
    public AluraServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public AluraServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        // Create map of enviroment
        StringBuilder urlDatasource = new StringBuilder();
        urlDatasource
                .append("jdbc:mysql://")
                .append(Fn.importValue("pedidos-db-endpoint"))
                .append(":3306/alurafood_order?createDatabaseIfNotExist=true");

        Map<String, String> environment = new HashMap<>();
        environment.put("SPRING_DATASOURCE_URL", urlDatasource.toString());
        environment.put("SPRING_DATASOURCE_USERNAME", "admin");
        environment.put("SPRING_DATASOURCE_PASSWORD", Fn.importValue("pedidos-db-senha"));


        // Create a load-balanced Fargate service and make it public
        ApplicationLoadBalancedFargateService fargateService = ApplicationLoadBalancedFargateService.Builder.create(this, "AluraService")
                .serviceName("alura-service-greeting")
                .cluster(cluster)           // Required
                .cpu(512)                   // Default is 256
                .desiredCount(1)            // Default is 1
                .listenerPort(8080)
                .assignPublicIp(true)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .image(ContainerImage.fromRegistry("teteo/order-ms"))
                                .containerName("app-greeting")
                                .environment(environment)
                                .containerPort(8080)
                                .build())
                .memoryLimitMiB(2048)       // Default is 512
                .publicLoadBalancer(true)   // Default is true
                .build();

        // Setting HealtCheck because the error with 404
        fargateService
                .getTargetGroup()
                .configureHealthCheck(HealthCheck.builder()
                        .port("8080")
                        .path("/")
                        .interval(Duration.seconds(30)) // Intervalo entre verificações
                .healthyHttpCodes("200-499")   // Códigos HTTP que indicam que o serviço está saudável
                .unhealthyThresholdCount(2).build());

        // Setting Auto Scaling
        ScalableTaskCount scalableTarget = fargateService.getService().autoScaleTaskCount(EnableScalingProps.builder()
                .minCapacity(1)
                .maxCapacity(3)
                .build());

        scalableTarget.scaleOnCpuUtilization("CpuScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(70)
                .scaleInCooldown(Duration.minutes(3))
                .scaleOutCooldown(Duration.minutes(5))
                .build());

        scalableTarget.scaleOnMemoryUtilization("MemoryScaling", MemoryUtilizationScalingProps.builder()
                .targetUtilizationPercent(70)
                .scaleInCooldown(Duration.minutes(3))
                .scaleOutCooldown(Duration.minutes(5))
                .build());
    }
}
