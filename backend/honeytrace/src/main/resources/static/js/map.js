// ======================================
// HoneyTrace Attack Map
// ======================================

let map;
let markers = [];

// ================================
// Initialize Map
// ================================

function initializeMap() {

    map = L.map("attackMap").setView([20, 0], 2);

    L.tileLayer(
        "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
        {
            attribution: "&copy; OpenStreetMap Contributors"
        }
    ).addTo(map);

}

// ================================
// Load Attackers
// ================================

async function loadAttackMap() {

    try {

        const response = await fetch("/api/map");

        const attackers = await response.json();

        // Remove old markers

        markers.forEach(marker => {

            map.removeLayer(marker);

        });

        markers = [];

        // Clear table

        const tbody =
            document.getElementById("attackTable");

        tbody.innerHTML = "";

        attackers.forEach(attacker => {

            // ==========================
            // Add Table Row
            // ==========================

            tbody.innerHTML += `

            <tr>

                <td>${attacker.id}</td>

                <td>${attacker.ipAddress}</td>

                <td>${attacker.country ?? "Unknown"}</td>

                <td>${attacker.city ?? "Unknown"}</td>

                <td>${attacker.riskScore}</td>

            </tr>

            `;

            // ==========================
            // Add Marker
            // ==========================

            if (
                attacker.latitude != null &&
                attacker.longitude != null
            ) {

                const marker = L.marker([
                    attacker.latitude,
                    attacker.longitude
                ]).addTo(map);

                marker.bindPopup(

                    `<b>${attacker.ipAddress}</b><br>

                     ${attacker.city}, ${attacker.country}<br>

                     Risk Score : ${attacker.riskScore}`

                );

                markers.push(marker);

            }

        });

    }

    catch (e) {

        console.log("Map Error", e);

    }

}

// ================================
// Initial Load
// ================================

initializeMap();

loadAttackMap();

// ================================
// Auto Refresh
// ================================

setInterval(() => {

    loadAttackMap();

}, 5000);