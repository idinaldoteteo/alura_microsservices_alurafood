package com.myorg;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.rds.*;
import software.constructs.Construct;

import java.util.Collections;

public class AluraRdsStack extends Stack {
    public AluraRdsStack(final Construct scope, final String id, final Vpc vpc) {
        this(scope, id, null, vpc);
    }

    public AluraRdsStack(final Construct scope, final String id, final StackProps props, final Vpc vpc) {
        super(scope, id, props);

        // configuration one - password
        CfnParameter senha = CfnParameter.Builder.create(this, "senha")
                .type("String")
                .description("Senha do database order-ms")
                .build();

        // configuraton two - security group
        ISecurityGroup iSecurityGroup = SecurityGroup.fromSecurityGroupId(this, id, vpc.getVpcDefaultSecurityGroup());
        iSecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(3306));

        // creating instance of database
        DatabaseInstance database = DatabaseInstance.Builder
                .create(this, "Rds-pedidos")
                .instanceIdentifier("alura-aws-pedido-db")
                .engine(DatabaseInstanceEngine.mysql(MySqlInstanceEngineProps.builder()
                        .version(MysqlEngineVersion.VER_8_0)
                        .build()))
                .vpc(vpc)
                .credentials(Credentials.fromUsername("admin",
                CredentialsFromUsernameOptions.builder()
                        .password(SecretValue.unsafePlainText(senha.getValueAsString()))
                        .build()))
                .instanceType(InstanceType.of(InstanceClass.T4G, InstanceSize.MICRO))
                .multiAz(false)
                .allocatedStorage(10)
                .securityGroups(Collections.singletonList(iSecurityGroup))
                .vpcSubnets(SubnetSelection.builder()
                        .subnets(vpc.getPrivateSubnets())
                        .build())
                .build();

        // Exposing some information to the application
        CfnOutput.Builder.create(this, "pedidos-db-endpoint")
                .exportName("pedidos-db-endpoint")
                .value(database.getDbInstanceEndpointAddress())
                .build();

        CfnOutput.Builder.create(this, "pedidos-db-senha")
                .exportName("pedidos-db-senha")
                .value(senha.getValueAsString())
                .build();

    }
}
