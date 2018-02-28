// Every jenkins file should start with either a Declarative or Scripted Pipeline entry point.
node {
    //Utilizing a try block so as to make the code cleaner and send slack notification in case of any error
    try {
        //Call function to send a message to Slack
        notifyBuild('STARTED')
        // Global variable declaration
        def project = 'dukecon_android'
        def appName = 'Dukecon Android'
        
//        agent {
//           docker { image 'node:7-alpine' }
//        }

        // Stage, is to tell the Jenkins that this is the new process/step that needs to be executed
        stage('Checkout') {
            // Pull the code from the repo
            checkout scm
        }

        stage('Build Image') {
            // Build our docker Image
            sh("docker build -t ${project} .")
        }
     

        stage('Run application test') {
            def workspace = pwd()
            sh("docker run --rm -v $workspace:/opt/workspace -u `id -u` -w /opt/workspace ${project} ./gradlew --stacktrace --info test")
        } 

        stage('Build application') {
            def workspace = pwd()
            sh("docker run --rm -v $workspace:/opt/workspace -u `id -u` -w /opt/workspace ${project} ./gradlew --stacktrace --info assemble")
        }       

        stage("Archive")   {
            // move all apk file from various build variants folder into working path
            sh("find ${WORKSPACE} -name '*.apk' -exec cp {} ${WORKSPACE} \\;")
			archive '*.apk'
		} 
        stage("Upload/Clean WS")   {
			step([$class: 'WsCleanup', cleanWhenAborted: false, cleanWhenFailure: true, cleanWhenNotBuilt: true, cleanWhenUnstable: true])
		}
    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    } finally {
    }
}