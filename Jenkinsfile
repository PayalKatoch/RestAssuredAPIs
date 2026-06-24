pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t api-automation .'
            }
        }

        stage('Run API Tests') {
            steps {
                sh 'docker run --rm api-automation'
            }
        }
    }
}