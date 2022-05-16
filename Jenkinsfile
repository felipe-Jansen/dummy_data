pipeline {
    agent any
    parameters{
        text(name:'LANGUAGE', defaultValue: '', description:'Type the language (Ex.: en_US; pt_BR)'),
        text(name:'AMOUNT', defaultValue: '', description:'Type how many accounts do you want'),
        text(name:'INITIAL_DATE', defaultValue: '', description:'Type the initial date (yyyy-mm-dd)')
        text(name:'FINAL_DATE', defaultValue: '', description:'Type the final date (yyyy-mm-dd)')
        text(name:'EXERCISE', defaultValue: '', description:'How many exercises per day?')
        text(name:'FOOD', defaultValue: '', description:'How many foods per day?')
        text(name:'BOLUS', defaultValue: '', description:'How many bolus per day?')
        text(name:'READING', defaultValue: '', description:'How many readings per day?')
    }
    stages {
        stage('Gerador') {
            steps {
                script {
                    sh "./gradlew bootRun --args='${env.LANGUAGE} ${env.AMOUNT} ${env.INITIAL_DATE} ${env.FINAL_DATE} exercise=${env.EXERCISE} food=${env.FOOD} bolus=${env.BOLUS} reading=${env.READING}'"
                }
            }
        }
    }
}
