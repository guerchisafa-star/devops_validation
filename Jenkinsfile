pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        DOCKER_IMAGE = "safagr/backend"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('1. Checkout Git') {
            steps {
                script {
                    def ghHost = "github.com"
                    def ghPath = "guerchisafa-star/devops_validation.git"
                    git branch: 'backend', url: "https://${ghHost}/${ghPath}"
                }
            }
        }

        stage('2. Build Maven & Tests') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('3. Build Docker Image') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} -t ${DOCKER_IMAGE}:latest .'
            }
        }

        stage('4. Push Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push ${DOCKER_IMAGE}:${IMAGE_TAG}'
                    sh 'docker push ${DOCKER_IMAGE}:latest'
                }
            }
        }

        stage('5. Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/postgres.yaml'
                sh 'kubectl apply -f k8s/deployment.yaml'
                sh 'kubectl rollout restart deployment/backend-deployment'
            }
        }
    }
}
