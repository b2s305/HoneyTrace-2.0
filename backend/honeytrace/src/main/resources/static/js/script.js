// =============================================
// HoneyTrace v2.0
// Dashboard Script
// Part 1
// =============================================

// =============================================
// Live Date & Time
// =============================================

function updateClock() {

    const now = new Date();

    const clock = document.getElementById("dateTime");

    if (clock) {

        clock.innerHTML =
            now.toLocaleDateString() +
            " | " +
            now.toLocaleTimeString();

    }

}

updateClock();
setInterval(updateClock, 1000);


// =============================================
// Dashboard Statistics
// =============================================

async function loadDashboard() {

    try {

        const response = await fetch("/api/dashboard");

        const data = await response.json();

        document.getElementById("attackersCount").textContent = data.attackers;
        document.getElementById("credentialsCount").textContent = data.credentials;
        document.getElementById("sessionsCount").textContent = data.sessions;

        const threat = document.getElementById("threatLevel");

        if (data.highRisk > 0) {

            threat.textContent = "HIGH";
            threat.style.color = "#dc3545";

        } else if (data.mediumRisk > 0) {

            threat.textContent = "MEDIUM";
            threat.style.color = "#ffc107";

        } else {

            threat.textContent = "LOW";
            threat.style.color = "#198754";

        }

    } catch (e) {

        console.error(e);

    }

}

// =============================================
// Attack Statistics Chart
// =============================================

let attackChart = null;

function initializeChart() {

    const canvas = document.getElementById("attackChart");

    if (!canvas)
        return;

    attackChart = new Chart(canvas, {

        type: "bar",

        data: {

            labels: [

                "Low",
                "Medium",
                "High"

            ],

            datasets: [

                {

                    label: "Attack Count",

                    data: [0,0,0],

                    borderWidth: 1

                }

            ]

        },

        options: {

            responsive: true,

            maintainAspectRatio: false,

            scales: {

                y: {

                    beginAtZero: true

                }

            }

        }

    });

}

initializeChart();


// =============================================
// Update Attack Chart
// =============================================

async function updateChart() {

    try {

        const response = await fetch("/api/dashboard");
        const data = await response.json();

        attackChart.data.datasets[0].data = [
            data.lowRisk,
            data.mediumRisk,
            data.highRisk
        ];

        attackChart.update();

    } catch (e) {

        console.error(e);

    }

}

// =============================================
// Initial Load
// =============================================

loadDashboard();

updateChart();


// =============================================
// Auto Refresh
// =============================================

setInterval(() => {

    loadDashboard();

    updateChart();

},5000);

// =============================================
// HoneyTrace v2.0
// Part 2
// World Map + Attack Feed + Search
// =============================================

// =============================================
// World Attack Map
// =============================================

let map = null;
let markers = [];

function initializeMap() {

    const mapDiv = document.getElementById("attackMap");

    if (!mapDiv)
        return;

    map = L.map("attackMap").setView([20, 0], 2);

    L.tileLayer(
        "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
        {
            attribution: "&copy; OpenStreetMap"
        }
    ).addTo(map);

}

initializeMap();


// =============================================
// Load Attackers On Map
// =============================================

async function loadAttackMap() {

    if (!map)
        return;

    try {

        markers.forEach(marker => {

            map.removeLayer(marker);

        });

        markers = [];

        const response =
            await fetch("/api/map");

        const attackers =
            await response.json();

        attackers.forEach(attacker => {

            if (
                attacker.latitude != null &&
                attacker.longitude != null
            ) {

                const marker = L.marker([
                    attacker.latitude,
                    attacker.longitude
                ])
                .addTo(map)
                .bindPopup(
                    "<b>" + attacker.ipAddress + "</b><br>" +
                    attacker.city + ", " +
                    attacker.country +
                    "<br><b>Risk:</b> " +
                    attacker.riskScore
                );

                markers.push(marker);

            }

        });

    }

    catch (e) {

        console.log("Map Error", e);

    }

}


// =============================================
// Latest Attack Feed
// =============================================

async function loadAttackFeed() {

    try {

        const response =
            await fetch("/api/attackers");

        const attackers =
            await response.json();

        const tbody =
            document.querySelector("table tbody");

        if (!tbody)
            return;

        tbody.innerHTML = "";

        if (attackers.length === 0) {

            tbody.innerHTML = `
            <tr>
                <td colspan="6" class="text-center">
                    No attacks detected.
                </td>
            </tr>`;

            return;

        }

        attackers.forEach(attacker => {

            let badge =
                '<span class="badge bg-success">LOW</span>';

            if (attacker.riskScore >= 70) {

                badge =
                '<span class="badge bg-danger">HIGH</span>';

            }

            else if (attacker.riskScore >= 30) {

                badge =
                '<span class="badge bg-warning text-dark">MEDIUM</span>';

            }

            tbody.innerHTML += `

            <tr>

                <td>${attacker.id}</td>

                <td>${attacker.ipAddress}</td>

                <td>${attacker.country ?? "Unknown"}</td>

                <td>${attacker.city ?? "Unknown"}</td>

                <td>${badge}</td>

                <td>${attacker.lastSeen ?? ""}</td>

            </tr>

            `;

        });

    }

    catch(e){

        console.log("Attack Feed Error", e);

    }

}


// =============================================
// Live Search
// =============================================

const searchBox =
    document.getElementById("searchBox");

if(searchBox){

searchBox.addEventListener("keyup",async function(){

    const keyword=this.value;

    try{

        const response=
            await fetch("/api/search?keyword="+encodeURIComponent(keyword));

        const attackers=
            await response.json();

        const tbody=
            document.querySelector("table tbody");

        tbody.innerHTML="";

        attackers.forEach(attacker=>{

            let badge=
            '<span class="badge bg-success">LOW</span>';

            if(attacker.riskScore>=70){

                badge=
                '<span class="badge bg-danger">HIGH</span>';

            }

            else if(attacker.riskScore>=30){

                badge=
                '<span class="badge bg-warning text-dark">MEDIUM</span>';

            }

            tbody.innerHTML+=`

            <tr>

                <td>${attacker.id}</td>

                <td>${attacker.ipAddress}</td>

                <td>${attacker.country ?? "Unknown"}</td>

                <td>${attacker.city ?? "Unknown"}</td>

                <td>${badge}</td>

                <td>${attacker.lastSeen ?? ""}</td>

            </tr>

            `;

        });

    }

    catch(e){

        console.log(e);

    }

});

}


// =============================================
// Initial Load
// =============================================

loadAttackMap();

loadAttackFeed();


// =============================================
// Auto Refresh
// =============================================

setInterval(()=>{

    loadAttackMap();

    loadAttackFeed();

},5000);

// =============================================
// HoneyTrace v2.0
// Part 3
// AI Analysis + Timeline + Auto Refresh
// =============================================


// =============================================
// AI Threat Analysis
// =============================================

async function loadAIAnalysis() {

    try {

        const response = await fetch("/api/analysis");
        const data = await response.json();

        document.getElementById("attackType").innerHTML =
            data.attackType;

			document.getElementById("confidence").innerHTML =
			data.confidence + "%";

			document.getElementById("confidenceBar").style.width =
			data.confidence + "%";
        document.getElementById("recommendation").innerHTML =
            data.recommendation;

        const threat =
            document.getElementById("threatLevel");

        threat.innerHTML =
            data.threatLevel;

			if(data.threatLevel=="HIGH"){

			    threat.className="text-danger";

			    document.getElementById("confidenceBar").className =
			    "progress-bar bg-danger";

			}

			else if(data.threatLevel=="MEDIUM"){

			    threat.className="text-warning";

			    document.getElementById("confidenceBar").className =
			    "progress-bar bg-warning";

			}

			else{

			    threat.className="text-success";

			    document.getElementById("confidenceBar").className =
			    "progress-bar bg-success";

			}

        else if (data.threatLevel === "MEDIUM") {

            threat.className = "text-warning";

        }

        else {

            threat.className = "text-success";

        }

    }

    catch (e) {

        console.log("AI Analysis Error", e);

    }

}



// =============================================
// Timeline
// =============================================

async function loadTimeline() {

    const timeline =
        document.getElementById("timeline");

    if (!timeline)
        return;

    try {

        const response =
            await fetch("/api/timeline");

        const events =
            await response.json();

        timeline.innerHTML = "";

        if (events.length === 0) {

            timeline.innerHTML =
                "<p>No activity.</p>";

            return;

        }

        events.forEach(event => {

            timeline.innerHTML += `

            <div class="border-bottom p-2">

                <b>${event.time}</b>

                <br>

                ${event.message}

            </div>

            `;

        });

    }

    catch (e) {

        console.log("Timeline Error");

    }

}



// =============================================
// Animated Counter
// =============================================

function animateCounter(id, value) {

    const element =
        document.getElementById(id);

    if (!element)
        return;

    element.innerHTML = value;

}



// =============================================
// System Status
// =============================================

function updateSystemStatus() {

    const status =
        document.getElementById("systemStatus");

    if (status) {

        status.innerHTML = "ONLINE";
        status.className = "text-success";

    }

}

updateSystemStatus();



// =============================================
// Live Threat Level
// =============================================

async function updateThreatLevel() {

    try {

        const response =
            await fetch("/api/dashboard");

        const data =
            await response.json();

        const threat =
            document.getElementById("liveThreat");

        if (!threat)
            return;

        if (data.highRisk > 0) {

            threat.innerHTML = "HIGH";
            threat.className = "text-danger";

        }

        else if (data.mediumRisk > 0) {

            threat.innerHTML = "MEDIUM";
            threat.className = "text-warning";

        }

        else {

            threat.innerHTML = "LOW";
            threat.className = "text-success";

        }

    }

    catch (e) {

        console.log(e);

    }

}



// =============================================
// Initial Load
// =============================================

loadAIAnalysis();

loadTimeline();

updateThreatLevel();



// =============================================
// Auto Refresh
// =============================================

setInterval(() => {

    loadDashboard();

    updateChart();

    loadAttackFeed();

    loadAttackMap();

    loadAIAnalysis();

    loadTimeline();

    updateThreatLevel();

}, 5000);



// =============================================
// HoneyTrace v2.0
// End of Part 3
// =============================================

// =============================================
// HoneyTrace v2.0
// Part 4
// Final Cleanup + WebSocket + Effects
// =============================================


// =============================================
// Dashboard Fade Animation
// =============================================

function animateCards() {

    document.querySelectorAll(".dashboard-card").forEach((card, index) => {

        card.style.opacity = "0";

        card.style.transform = "translateY(20px)";

        setTimeout(() => {

            card.style.transition = "0.5s";

            card.style.opacity = "1";

            card.style.transform = "translateY(0px)";

        }, index * 120);

    });

}

window.addEventListener("load", animateCards);


// =============================================
// Auto Scroll Timeline
// =============================================

function scrollTimeline() {

    const timeline = document.getElementById("timeline");

    if (!timeline)
        return;

    timeline.scrollTop = timeline.scrollHeight;

}

setInterval(scrollTimeline, 5000);


// =============================================
// Browser Notification
// =============================================

function notifyAttack(ip) {

    if (!("Notification" in window))
        return;

    if (Notification.permission === "granted") {

        new Notification("HoneyTrace Alert", {

            body: "New attack detected from " + ip,

            icon: "/images/logo.png"

        });

    }

}

if ("Notification" in window) {

    Notification.requestPermission();

}


// =============================================
// WebSocket
// =============================================

if (typeof SockJS !== "undefined" &&
    typeof Stomp !== "undefined") {

    try {

        const socket = new SockJS("/ws");

        const stompClient = Stomp.over(socket);

        stompClient.connect({}, function () {

            console.log("WebSocket Connected");

            stompClient.subscribe("/topic/attacks", function (message) {

                const attack =
                    JSON.parse(message.body);

                notifyAttack(attack.ipAddress);

                loadDashboard();

                loadAttackFeed();

                loadAttackMap();

                loadAIAnalysis();

                updateChart();

            });

        });

    }

    catch (e) {

        console.log("WebSocket unavailable");

    }

}


// =============================================
// Heartbeat
// =============================================

setInterval(() => {

    console.log("HoneyTrace Running...");

}, 60000);


// =============================================
// Window Resize
// =============================================

window.addEventListener("resize", () => {

    if (map) {

        map.invalidateSize();

    }

});


// =============================================
// Initialize Everything
// =============================================

document.addEventListener("DOMContentLoaded", () => {

    loadDashboard();

    updateChart();

    loadAttackFeed();

    loadAttackMap();

    loadAIAnalysis();

    loadTimeline();

    updateThreatLevel();

    animateCards();

});
function showToast(message){

    document.getElementById("toastMessage").innerHTML = message;

    const toast = new bootstrap.Toast(
        document.getElementById("attackToast")
    );

    toast.show();
}


// =============================================
// HoneyTrace v2.0
// Developed by Aditi
// Final Production Version
// =============================================