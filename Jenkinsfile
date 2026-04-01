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
                echo 'Compiling with Java 21...'
                bat 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Creating JAR...'
                bat 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
