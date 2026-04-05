pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = 'docker-hub-credentials'
        DOCKER_IMAGE = 'alihaider58162/car-app'
    }

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

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    docker.build("${DOCKER_IMAGE}:${BUILD_NUMBER}")
                    docker.build("${DOCKER_IMAGE}:latest")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                echo 'Pushing to Docker Hub...'
                script {
                    docker.withRegistry('', DOCKER_HUB_CREDENTIALS) {
                        docker.image("${DOCKER_IMAGE}:${BUILD_NUMBER}").push()
                        docker.image("${DOCKER_IMAGE}:latest").push()
                    }
                }
            }
        }

        stage('Deploy & Run Container') {
            steps {
                echo 'Deploying container...'
                script {
                    bat '''
                        docker stop car-app || true
                        docker rm car-app || true
                        docker run -d -p 7779:7779 --name car-app alihaider58162/car-app:latest
                    '''
                }
            }
        }

        stage('Health Check') {
            steps {
                echo 'Checking application health...'
                script {
                    sleep time: 15, unit: 'SECONDS'
                    bat 'curl -f http://localhost:7779/actuator/health || exit 1'
                }
            }
        }
    }
    
    post {
        success {
            echo '🎉 Full CI/CD Pipeline Completed!'
            echo '✅ App is running on Docker at http://localhost:7779'
        }
        failure {
            echo '❌ Pipeline failed! Check logs.'
        }
    }
}
