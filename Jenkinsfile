pipeline {

    agent any

    tools {
    jdk 'jdk25'
    maven 'maven3'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'master',
                url: 'https://github.com/yashcsingh/MALT-copy.git'
            }
        }

        stage('Build Project') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Run Test Cases') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

    }
}