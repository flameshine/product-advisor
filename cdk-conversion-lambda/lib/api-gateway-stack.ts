import { Stack, StackProps, Fn } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { Function } from 'aws-cdk-lib/aws-lambda';
import { HttpApi, HttpMethod } from '@aws-cdk/aws-apigatewayv2-alpha';
import { HttpLambdaIntegration } from '@aws-cdk/aws-apigatewayv2-integrations-alpha';
import {Cors} from "aws-cdk-lib/aws-apigateway";

export class ApiGatewayStack extends Stack {

    constructor(scope: Construct, id: string, props?: StackProps) {
        super(scope, id, props);

        const api = new HttpApi(this, 'ConversionLambdaHttpApi', {
            apiName: 'conversion-lambda-http-api',
            corsPreflight: {
                allowOrigins: Cors.ALL_ORIGINS,
            }
        });

        const conversionLambdaHandlerArn = Fn.importValue('conversion-lambda-handler-arn');
        const conversionLambdaHandler = Function.fromFunctionArn(this, 'ConversionLambdaHandler', conversionLambdaHandlerArn);
        const conversionLambdaIntegration = new HttpLambdaIntegration('ConversionLambdaIntegration', conversionLambdaHandler);

        api.addRoutes({
            path: '/convert',
            methods: [ HttpMethod.POST ],
            integration: conversionLambdaIntegration,
        });
    }
}