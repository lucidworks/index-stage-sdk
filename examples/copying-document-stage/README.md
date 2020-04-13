# Sample duplicating plugin stage.
Sample project with two single indexing stage, that is simply duplicating every incoming document. 

This is also a demonstration how single stage can emit multiple documents while processing single input.


# Build
To create a plugin call ```./gradlew -p examples/copying-plugin-stage assemblePlugin``` in the main folder.
This will create a plugin zip file (with required manifest file) within the ```build/libs``` folder

# Deploy
You can deploy your plugin with UI ("Blobs" section) or using the curl command:

```bash
curl -u [user]:[password] -X PUT -H "Content-Type:application/zip" --data-binary @copying-plugin-stage-0.0.1.zip https://[fusion url]/api/index-stages/plugins
```

After correct deployment new stages should be visible on the `Stages` list in the Fusion UI