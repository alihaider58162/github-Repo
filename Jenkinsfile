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

        stage('Compile') {
            steps {
                echo 'Compiling...'
                bat 'cd JenkinsCICD && mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                bat 'cd JenkinsCICD && mvn test'
            }
            post {
                always {
                    // Publish test reports
                    junit 'JenkinsCICD/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Creating JAR...'
                bat 'cd JenkinsCICD && mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts 'JenkinsCICD/target/*.jar'
                }
            }
        }
    }
    
    post {
        success {
            echo '🎉 Build and tests completed successfully!'
        }
        failure {
            echo '❌ Build or tests failed!'
        }
    }
}
