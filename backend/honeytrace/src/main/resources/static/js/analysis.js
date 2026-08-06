// =====================================
// HoneyTrace AI Analysis
// =====================================

async function loadAnalysis() {

    try {

        const response = await fetch("/api/analysis");

        const data = await response.json();

        // =============================
        // AI Cards
        // =============================

        document.getElementById("attackType").innerHTML =
            data.attackType || "Unknown";

        document.getElementById("confidence").innerHTML =
            (data.confidence || 0) + "%";

        document.getElementById("recommendation").innerHTML =
            data.recommendation || "-";

        document.getElementById("topCountry").innerHTML =
            data.topCountry || "-";

        document.getElementById("topIp").innerHTML =
            data.topIp || "-";

        document.getElementById("totalAttackers").innerHTML =
            data.totalAttackers || 0;

        document.getElementById("highRiskCount").innerHTML =
            data.highRiskCount || 0;

        // =============================
        // Threat Level
        // =============================

        const threat =
            document.getElementById("threatLevel");

        threat.innerHTML =
            data.threatLevel;

        if(data.threatLevel==="HIGH"){

            threat.style.color="#dc2626";

        }

        else if(data.threatLevel==="MEDIUM"){

            threat.style.color="#f59e0b";

        }

        else{

            threat.style.color="#16a34a";

        }

        // =============================
        // Risk Score
        // =============================

        let score=0;

        if(data.highRiskCount>0){

            score=90;

        }

        else if(data.threatLevel==="MEDIUM"){

            score=60;

        }

        else{

            score=25;

        }

        document.getElementById("riskScore").innerHTML=
            score+"/100";

    }

    catch(e){

        console.log(e);

    }

}


// =====================================
// Timeline
// =====================================

async function loadTimeline(){

    try{

        const response=
            await fetch("/api/timeline");

        const events=
            await response.json();

        const timeline=
            document.getElementById("timeline");

        timeline.innerHTML="";

        if(events.length===0){

            timeline.innerHTML=

            "<div class='p-3'>No recent activity.</div>";

            return;

        }

        events.forEach(event=>{

            timeline.innerHTML+=`

            <div class="border-bottom p-3">

                <b>${event.time}</b>

                <br>

                ${event.message}

            </div>

            `;

        });

    }

    catch(e){

        console.log(e);

    }

}


// =====================================
// Load
// =====================================

loadAnalysis();

loadTimeline();

setInterval(()=>{

    loadAnalysis();

    loadTimeline();

},5000);