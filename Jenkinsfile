pipeline {
    agent any

    tools {
        maven 'maven-3'
        jdk 'jdk-21'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Fetching code...'
                checkout scm
            }
        }

        stage('Compile & Package') {
            steps {
                echo 'Moving to JenkinsCICD folder and building...'
                bat '''
                    cd JenkinsCICD
                    mvn clean package
                '''
            }
            post {
                success {
                    // JAR ab JenkinsCICD/target/ folder mein banegi
                    archiveArtifacts 'JenkinsCICD/target/*.jar'
                }
            }
        }
    }
}
