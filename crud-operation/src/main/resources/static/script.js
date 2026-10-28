
const API_URL = "/api/students";

const form = document.getElementById("studentForm");
const studentId = document.getElementById("studentId");
const nameInput = document.getElementById("name");
const emailInput = document.getElementById("email");
const courseInput = document.getElementById("course");
const marksInput = document.getElementById("marks");
const tableBody = document.getElementById("studentTable");
const studentCount = document.getElementById("studentCount");
const message = document.getElementById("message");
const submitBtn = document.getElementById("submitBtn");
const cancelBtn = document.getElementById("cancelBtn");
const formTitle = document.getElementById("formTitle");

let students = [];

async function apiRequest(url, options = {}) {
    const response = await fetch(url, {
        ...options,
        headers: {
            ...(options.body
                ? { "Content-Type": "application/json" }
                : {}),
            ...options.headers
        }
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(
            errorText || `Request failed (${response.status})`
        );
    }

    if (response.status === 204) {
        return null;
    }

    const contentType = response.headers.get("content-type") || "";

    return contentType.includes("application/json")
        ? response.json()
        : null;
}

function showMessage(text, isError = false) {
    message.textContent = text;
    message.classList.toggle("error", isError);
}

function addCell(row, value) {
    const cell = document.createElement("td");
    cell.textContent = value ?? "";
    row.appendChild(cell);
    return cell;
}

async function loadStudents() {
    tableBody.replaceChildren();

    const loadingRow = document.createElement("tr");
    const loadingCell = addCell(loadingRow, "Loading students...");
    loadingCell.colSpan = 6;
    tableBody.appendChild(loadingRow);

    try {
        students = await apiRequest(API_URL);
        renderStudents();
    } catch (error) {
        students = [];
        renderStudents();

        showMessage(
            "Unable to load students. Check whether Spring Boot is running.",
            true
        );
    }
}

function renderStudents() {
    tableBody.replaceChildren();
    studentCount.textContent = `${students.length} student(s)`;

    if (students.length === 0) {
        const row = document.createElement("tr");
        const cell = addCell(row, "No student records found.");
        cell.colSpan = 6;
        tableBody.appendChild(row);
        return;
    }

    students.forEach(student => {
    console.log("student details = ",student);
    console.log("student details = ",student.firstName);
        const row = document.createElement("tr");

        addCell(row, student.studentId);
        addCell(row, student.firstName);
        addCell(row, student.lastName);
        addCell(row, student.email);
        addCell(row, student.phone);
        addCell(row, student.dateOfBirth);
        addCell(row, student.course);
        addCell(row, student.marks);

        const actionCell = addCell(row, "");
        const actions = document.createElement("div");
        actions.className = "row-actions";

        const editBtn = document.createElement("button");
        editBtn.type = "button";
        editBtn.textContent = "Edit";
        editBtn.className = "secondary";
        editBtn.addEventListener("click", () =>
            editStudent(student.id)
        );

        const deleteBtn = document.createElement("button");
        deleteBtn.type = "button";
        deleteBtn.textContent = "Delete";
        deleteBtn.className = "danger";
        deleteBtn.addEventListener("click", () =>
            deleteStudent(student.id)
        );

        actions.append(editBtn, deleteBtn);
        actionCell.appendChild(actions);
        tableBody.appendChild(row);
    });
}

// CREATE and UPDATE
form.addEventListener("submit", async event => {
    event.preventDefault();

    const id = studentId.value;
    const marksValue = marksInput.value.trim();
    const marks = Number(marksValue);

    const student = {
        name: nameInput.value.trim(),
        email: emailInput.value.trim(),
        course: courseInput.value.trim(),
        marks
    };

    if (
        !student.name ||
        !student.email ||
        !student.course ||
        !status.marks ||
    ) {
        showMessage("Please enter valid student details.", true);
        return;
    }

    submitBtn.disabled = true;

    try {
        await apiRequest(
            id ? `${API_URL}/${id}` : API_URL,
            {
                method: id ? "PUT" : "POST",
                body: JSON.stringify(student)
            }
        );

        resetForm();
        await loadStudents();

        showMessage(
            id
                ? "Student updated successfully."
                : "Student added successfully."
        );
    } catch (error) {
        showMessage(error.message || "Unable to save student.", true);
    } finally {
        submitBtn.disabled = false;
    }
});

// EDIT
function editStudent(id) {
    const student = students.find(item => item.id === id);

    if (!student) {
        showMessage("Student not found.", true);
        return;
    }

    studentId.value = student.id;
    nameInput.value = student.firstName;
    emailInput.value = student.email;
    courseInput.value = student.course;
    marksInput.value = student.marks;

    formTitle.textContent = "Edit Student";
    submitBtn.textContent = "Update Student";
    cancelBtn.classList.remove("hidden");

    form.scrollIntoView({ behavior: "smooth", block: "start" });
    nameInput.focus();
}

// DELETE
async function deleteStudent(id) {
    if (!window.confirm("Are you sure you want to delete this student?")) {
        return;
    }

    try {
        await apiRequest(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (studentId.value === String(id)) {
            resetForm();
        }

        await loadStudents();
        showMessage("Student deleted successfully.");
    } catch (error) {
        showMessage(error.message || "Unable to delete student.", true);
    }
}

// RESET FORM
function resetForm() {
    form.reset();
    studentId.value = "";
    formTitle.textContent = "Add New Student";
    submitBtn.textContent = "Add Student";
    cancelBtn.classList.add("hidden");
}

cancelBtn.addEventListener("click", () => {
    resetForm();
    showMessage("");
});

document.getElementById("refreshBtn").addEventListener(
    "click",
    loadStudents
);

loadStudents();
