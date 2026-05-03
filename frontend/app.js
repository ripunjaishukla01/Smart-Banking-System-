const API_BASE = "http://localhost:8080/api";

function saveAuth(data) {
  localStorage.setItem("token", data.token);
  localStorage.setItem("fullName", data.fullName);
  localStorage.setItem("email", data.email);
  localStorage.setItem("role", data.role);
  localStorage.setItem("accountNumber", data.accountNumber);
}

function getToken() {
  return localStorage.getItem("token");
}

function authHeaders() {
  return {
    "Content-Type": "application/json",
    "Authorization": "Bearer " + getToken()
  };
}

function showSection(sectionId) {
  document.querySelectorAll(".section").forEach(section => {
    section.classList.add("hidden");
  });

  const section = document.getElementById(sectionId);
  if (section) {
    section.classList.remove("hidden");
  }

  if (sectionId === "transactions") {
    loadTransactions();
  }
}

function showAdminSection(sectionId) {
  document.querySelectorAll(".section").forEach(section => {
    section.classList.add("hidden");
  });

  const section = document.getElementById(sectionId);
  if (section) {
    section.classList.remove("hidden");
  }
}

function register() {
  const data = {
    fullName: document.getElementById("fullName").value,
    email: document.getElementById("email").value,
    password: document.getElementById("password").value,
    initialBalance: parseFloat(document.getElementById("initialBalance").value)
  };

  fetch(`${API_BASE}/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  })
  .then(res => res.json())
  .then(data => {
    alert(data.message);
    if (data.token) {
      saveAuth(data);
      window.location.href = "dashboard.html";
    }
  })
  .catch(err => {
    console.error(err);
    alert("Registration failed");
  });
}

function login() {
  const data = {
    email: document.getElementById("loginEmail").value,
    password: document.getElementById("loginPassword").value
  };

  fetch(`${API_BASE}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  })
  .then(res => res.json())
  .then(data => {
    alert(data.message || "Login response received");
    if (data.token) {
      saveAuth(data);
      if (data.role === "ADMIN") {
        window.location.href = "admin.html";
      } else {
        window.location.href = "dashboard.html";
      }
    }
  })
  .catch(err => {
    console.error(err);
    alert("Login failed");
  });
}

function loadDashboard() {
  const accountNumber = localStorage.getItem("accountNumber");
  if (!accountNumber || !getToken()) {
    window.location.href = "index.html";
    return;
  }

  fetch(`${API_BASE}/banking/account/${accountNumber}`, {
    headers: authHeaders()
  })
  .then(res => res.json())
  .then(data => {
    document.getElementById("topUserName").innerText = data.fullName || "User";
    document.getElementById("userName").innerText = data.fullName || "";
    document.getElementById("userEmail").innerText = data.email || "";
    document.getElementById("userAccount").innerText = data.accountNumber || "";
    document.getElementById("userBalance").innerText = data.balance ?? 0;

    document.getElementById("cardName").innerText = data.fullName || "";
    document.getElementById("cardEmail").innerText = data.email || "";
    document.getElementById("cardAccount").innerText = data.accountNumber || "";
    document.getElementById("cardBalance").innerText = data.balance ?? 0;
  })
  .catch(err => {
    console.error(err);
    alert("Failed to load dashboard");
  });
}

function depositMoney() {
  const accountNumber = localStorage.getItem("accountNumber");
  const amount = parseFloat(document.getElementById("depositAmount").value);

  if (!amount || amount <= 0) {
    alert("Please enter valid deposit amount");
    return;
  }

  fetch(`${API_BASE}/banking/deposit`, {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify({ accountNumber, amount })
  })
  .then(res => res.json())
  .then(data => {
    alert(data.message);
    document.getElementById("depositAmount").value = "";
    loadDashboard();
  })
  .catch(err => {
    console.error(err);
    alert("Deposit failed");
  });
}

function withdrawMoney() {
  const accountNumber = localStorage.getItem("accountNumber");
  const amount = parseFloat(document.getElementById("withdrawAmount").value);

  if (!amount || amount <= 0) {
    alert("Please enter valid withdraw amount");
    return;
  }

  fetch(`${API_BASE}/banking/withdraw`, {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify({ accountNumber, amount })
  })
  .then(res => res.json())
  .then(data => {
    alert(data.message);
    document.getElementById("withdrawAmount").value = "";
    loadDashboard();
  })
  .catch(err => {
    console.error(err);
    alert("Withdraw failed");
  });
}

function transferMoney() {
  const senderAccount = localStorage.getItem("accountNumber");
  const receiverAccount = document.getElementById("receiverAccount").value;
  const amount = parseFloat(document.getElementById("transferAmount").value);

  if (!receiverAccount) {
    alert("Please enter receiver account number");
    return;
  }

  if (!amount || amount <= 0) {
    alert("Please enter valid transfer amount");
    return;
  }

  fetch(`${API_BASE}/banking/transfer`, {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify({ senderAccount, receiverAccount, amount })
  })
  .then(res => res.json())
  .then(data => {
    alert(data.message);
    document.getElementById("receiverAccount").value = "";
    document.getElementById("transferAmount").value = "";
    loadDashboard();
  })
  .catch(err => {
    console.error(err);
    alert("Transfer failed");
  });
}

function loadTransactions() {
  const accountNumber = localStorage.getItem("accountNumber");

  fetch(`${API_BASE}/banking/transactions/${accountNumber}`, {
    headers: authHeaders()
  })
  .then(res => res.json())
  .then(data => {
    const tbody = document.getElementById("transactionTableBody");
    tbody.innerHTML = "";

    if (!Array.isArray(data) || data.length === 0) {
      tbody.innerHTML = `<tr><td colspan="5">No transactions found</td></tr>`;
      return;
    }

    data.forEach(tx => {
      tbody.innerHTML += `
        <tr>
          <td>${tx.type}</td>
          <td>₹${tx.amount}</td>
          <td>${tx.senderAccount}</td>
          <td>${tx.receiverAccount}</td>
          <td>${tx.transactionTime}</td>
        </tr>
      `;
    });
  })
  .catch(err => {
    console.error(err);
    alert("Failed to load transactions");
  });
}

function addBeneficiary() {
  const name = document.getElementById("beneficiaryName").value;
  const account = document.getElementById("beneficiaryAccount").value;
  const bank = document.getElementById("beneficiaryBank").value;

  if (!name || !account || !bank) {
    alert("Please fill all beneficiary fields");
    return;
  }

  const list = document.getElementById("beneficiaryList");
  const li = document.createElement("li");
  li.innerText = `${name} | ${account} | ${bank}`;
  list.appendChild(li);

  document.getElementById("beneficiaryName").value = "";
  document.getElementById("beneficiaryAccount").value = "";
  document.getElementById("beneficiaryBank").value = "";

  alert("Beneficiary added successfully");
}

function generateStatement() {
  const month = document.getElementById("statementMonth").value;
  const result = document.getElementById("statementResult");

  if (!month) {
    result.innerText = "Please select a month.";
    return;
  }

  result.innerText = `Statement generated for ${month}. You can enhance this by linking backend PDF/Excel export later.`;
}

function requestLoan() {
  const type = document.getElementById("loanType").value;
  const amount = document.getElementById("loanAmount").value;
  const reason = document.getElementById("loanReason").value;
  const msg = document.getElementById("loanMessage");

  if (!type || !amount || !reason) {
    msg.innerText = "Please fill all loan request fields.";
    return;
  }

  msg.innerText = `Loan request submitted: ${type} loan of ₹${amount}.`;
  document.getElementById("loanType").value = "";
  document.getElementById("loanAmount").value = "";
  document.getElementById("loanReason").value = "";
}

function createFD() {
  const amount = document.getElementById("fdAmount").value;
  const years = document.getElementById("fdYears").value;
  const msg = document.getElementById("fdMessage");

  if (!amount || !years) {
    msg.innerText = "Please fill FD amount and duration.";
    return;
  }

  const maturity = (parseFloat(amount) + (parseFloat(amount) * 0.07 * parseInt(years))).toFixed(2);
  msg.innerText = `FD created successfully. Estimated maturity amount: ₹${maturity}`;
  document.getElementById("fdAmount").value = "";
  document.getElementById("fdYears").value = "";
}

function loadAdminDashboard() {
  if (!getToken()) {
    window.location.href = "index.html";
    return;
  }

  fetch(`${API_BASE}/admin/users`, {
    headers: authHeaders()
  })
  .then(res => res.json())
  .then(users => {
    document.getElementById("totalUsers").innerText = users.length;
    const table = document.getElementById("adminUsersTable");
    table.innerHTML = "";

    users.forEach(user => {
      table.innerHTML += `
        <tr>
          <td>${user.fullName}</td>
          <td>${user.email}</td>
          <td>${user.accountNumber}</td>
          <td>₹${user.balance}</td>
          <td>${user.role}</td>
        </tr>
      `;
    });
  });

  fetch(`${API_BASE}/admin/transactions`, {
    headers: authHeaders()
  })
  .then(res => res.json())
  .then(transactions => {
    document.getElementById("totalTransactions").innerText = transactions.length;
    const table = document.getElementById("adminTransactionsTable");
    table.innerHTML = "";

    transactions.forEach(tx => {
      table.innerHTML += `
        <tr>
          <td>${tx.type}</td>
          <td>₹${tx.amount}</td>
          <td>${tx.senderAccount}</td>
          <td>${tx.receiverAccount}</td>
          <td>${tx.transactionTime}</td>
        </tr>
      `;
    });
  });
}

function logout() {
  localStorage.clear();
  window.location.href = "index.html";
}