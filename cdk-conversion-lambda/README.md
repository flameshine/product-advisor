# cdk-conversion-lambda

An AWS CDK project, containing deployment for the Conversion Lambda stack and supportive components, which converts received audio buffers from WebM to WAV.

The main purpose of delegating this logic to the Cloud is twofold:

- avoid delegating the audio messages payloads processing to the user browser engines
- get on-hands experience interacting with AWS, refreshing the knowledge