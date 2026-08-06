// ==========================================
// HoneyTrace Attackers Module
// ==========================================

const API_URL = "/api/attackers";
const SEARCH_URL = "/api/search";

async function loadAttackers() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Unable to fetch attackers.");
        }

        const attackers = await response.json();

        renderTable(attackers);

    } catch (error) {

        console.error("HoneyTrace:", error);

        document.getElementById("attackerTable").innerHTML = `
            <tr>
                <td colspan="6" class="text-center text-danger">
                    Failed to load attackers.
                </td>
            </tr>
        `;
    }
}

// ==========================================
// Search
// ==========================================

async function searchAttackers(keyword) {

    try {

        let url = API_URL;

        if (keyword.trim() !== "") {
            url = SEARCH_URL + "?keyword=" + encodeURIComponent(keyword);
        }

        const response = await fetch(url);

        const attackers = await response.json();

        renderTable(attackers);

    } catch (error) {

        console.error(error);

    }

}

// ==========================================
// Render Table
// ==========================================

function renderTable(data) {

    const tbody = document.getElementById("attackerTable");

    tbody.innerHTML = "";

    if (data.length === 0) {

        tbody.innerHTML = `
            <tr>
                <td colspan="6" class="text-center">
                    No attackers found.
                </td>
            </tr>
        `;

        return;
    }

    data.forEach(attacker => {

        let badge = "bg-success";
        let risk = "LOW";

        if ((attacker.riskScore ?? 0) >= 70) {

            badge = "bg-danger";
            risk = "HIGH";

        }
        else if ((attacker.riskScore ?? 0) >= 30) {

            badge = "bg-warning text-dark";
            risk = "MEDIUM";

        }

        const row = `
            <tr>

                <td>${attacker.id}</td>

                <td>${attacker.ipAddress}</td>

                <td>${attacker.country ?? "Unknown"}</td>

                <td>${attacker.city ?? "Unknown"}</td>

                <td>
                    <span class="badge ${badge}">
                        ${risk}
                    </span>
                </td>

                <td>${formatDate(attacker.lastSeen)}</td>

            </tr>
        `;

        tbody.insertAdjacentHTML("beforeend", row);

    });

}

// ==========================================
// Date Formatting
// ==========================================

function formatDate(date) {

    if (!date)
        return "-";

    const d = new Date(date);

    return d.toLocaleString();

}

// ==========================================
// Search Box
// ==========================================

document.addEventListener("DOMContentLoaded", () => {

    loadAttackers();

    setInterval(loadAttackers, 5000);

    const search = document.getElementById("searchBox");

    if (search) {

        search.addEventListener("keyup", function () {

            searchAttackers(this.value);

        });

    }

});