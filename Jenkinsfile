pipeline {
    agent any

    environment {
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
                bat 'docker build -t alihaider58162/car-app:latest .'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                echo 'Pushing to Docker Hub...'
                withCredentials([string(credentialsId: 'docker-hub-password', variable: 'DOCKER_PASS')]) {
                    bat '''
                        echo %DOCKER_PASS% | docker login -u alihaider58162 --password-stdin
                        docker push alihaider58162/car-app:latest
                    '''
                }
            }
        }

        stage('Deploy & Run Container') {
            steps {
                echo 'Deploying container...'
                bat '''
                    docker stop car-app || echo "No container to stop"
                    docker rm car-app || echo "No container to remove"
                    docker run -d -p 7778:7778 --name car-app alihaider58162/car-app:latest
                '''
            }
        }
    }
    
    post {
        success {
            echo '🎉 Full pipeline completed!'
            echo '✅ App built, tested, pushed to Docker Hub, and container started!'
            echo '🌐 Check: http://localhost:7778'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}
