pipeline {
    agent any
    parameters{
        choice(name: 'ENVIRONMENT', choices: ['https://api.dev.lfsdigital.com', 'https://api.test.lfsdigital.com', 'https://api.pre-stage.lfsdigital.com'], description: 'Select the environment')
        text(name:'LANGUAGE', defaultValue: 'en_US', description:'Type the language (Ex.: en_US; pt_BR)')
        text(name:'AMOUNT', defaultValue: '5', description:'Type how many accounts do you want')
        text(name:'INITIAL_DATE', defaultValue: '2022-05-01', description:'Type the initial date (yyyy-mm-dd)')
        text(name:'FINAL_DATE', defaultValue: '', description:'Type the final date (yyyy-mm-dd)')
        text(name:'EXERCISE', defaultValue: '1', description:'How many exercises per day?')
        text(name:'FOOD', defaultValue: '4', description:'How many foods per day?')
        text(name:'BOLUS', defaultValue: '1', description:'How many bolus per day?')
        text(name:'READING', defaultValue: '5', description:'How many readings per day?')
    }
    stages {
        stage('Generating events') {
            steps {
                script {
                    sh "./gradlew bootRun --args='${env.LANGUAGE} ${env.AMOUNT} ${env.INITIAL_DATE} ${env.FINAL_DATE} exercise=${env.EXERCISE} food=${env.FOOD} bolus=${env.BOLUS}&Type=FAST reading=${env.READING}&Tag=MEAL_TAG_PRE_MEAL --host.domain=${env.ENVIRONMENT}'"
                }
            }
        }
    }
}
