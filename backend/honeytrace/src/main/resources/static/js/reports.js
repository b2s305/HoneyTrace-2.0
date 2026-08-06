async function loadReportData(){

    try{

        const response = await fetch("/api/dashboard");

        const data = await response.json();

        document.getElementById("attackersCount").innerHTML = data.attackers;

        document.getElementById("credentialsCount").innerHTML = data.credentials;

        document.getElementById("highRiskCount").innerHTML = data.highRisk;

        document.getElementById("topCountry").innerHTML = data.topCountry;

        document.getElementById("topUsername").innerHTML = data.topUsername;

    }

    catch(e){

        console.log(e);

    }

}

loadReportData();