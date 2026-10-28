
const API_URL = "/api/students";

const form = document.getElementById("studentForm");
const studentIdInput = document.getElementById("studentId");

const firstNameInput = document.getElementById("firstName");
const lastNameInput = document.getElementById("lastName");
const emailInput = document.getElementById("email");
const phoneInput = document.getElementById("phone");
const dateOfBirthInput = document.getElementById("dateOfBirth");
const genderInput = document.getElementById("gender");
const courseInput = document.getElementById("course");
const admissionDateInput = document.getElementById("admissionDate");
const marksInput = document.getElementById("marks");

const tableBody = document.getElementById("studentTable");
const studentCount = document.getElementById("studentCount");
const message = document.getElementById("message");
const submitBtn = document.getElementById("submitBtn");
const cancelBtn = document.getElementById("cancelBtn");
const formTitle = document.getElementById("formTitle");
const refreshBtn = document.getElementById("refreshBtn");

let students = [];

// =====================================================
// GET STUDENT ID
// =====================================================
function getStudentId(student) {
    return student.id ?? student.studentId;
}

// =====================================================
// API REQUEST HELPER
// =====================================================
async function apiRequest(url, options = {}) {
    const headers = {
        ...(options.body
            ? { "Content-Type": "application/json" }
            : {}),
        ...options.headers
    };

    const response = await fetch(url, {
        ...options,
        headers
    });

    if (!response.ok) {
        let errorMessage = `Request failed (${response.status})`;

        try {
            const errorData = await response.json();

            errorMessage =
                errorData.message ||
                errorData.error ||
                errorMessage;
        } catch {
            // Keep the default message if the response is not JSON.
        }

        throw new Error(errorMessage);
    }

    if (response.status === 204) {
        return null;
    }

    const contentType = response.headers.get("content-type") || "";

    if (contentType.includes("application/json")) {
        return response.json();
    }

    return null;
}

// =====================================================
// DISPLAY SUCCESS / ERROR MESSAGES
// =====================================================
function showMessage(text, isError = false) {
    message.textContent = text;
    message.classList.toggle("error", isError);
}

// =====================================================
// CREATE TABLE CELL
// =====================================================
function addCell(row, value) {
    const cell = document.createElement("td");
    cell.textContent = value ?? "";
    row.appendChild(cell);
    return cell;
}

// =====================================================
// FORMAT DATES
// =====================================================
function formatDate(value) {
    if (!value) {
        return "";
    }

    const parts = String(value).split("-");

    if (parts.length === 3 && parts[0].length === 4) {
        return `${parts[2]}/${parts[1]}/${parts[0]}`;
    }

    return String(value);
}

// =====================================================
// LOAD ALL STUDENTS
// GET /api/students
// =====================================================
async function loadStudents() {
    refreshBtn.disabled = true;
    studentCount.textContent = "Loading students...";

    try {
        const result = await apiRequest(API_URL);

        students = Array.isArray(result)
            ? result
            : Array.isArray(result?.content)
                ? result.content
                : [];

        renderStudents();
    } catch (error) {
        students = [];
        renderStudents();

        showMessage(
            error.message ||
            "Unable to load students. Check whether Spring Boot is running.",
            true
        );
    } finally {
        refreshBtn.disabled = false;
    }
}

// =====================================================
// RENDER STUDENT TABLE
// =====================================================
function renderStudents() {
    tableBody.replaceChildren();

    studentCount.textContent = `${students.length} student(s)`;

    if (students.length === 0) {
        const row = document.createElement("tr");
        const cell = addCell(row, "No student records found.");

        cell.colSpan = 11;
        tableBody.appendChild(row);
        return;
    }

    students.forEach(student => {
        const row = document.createElement("tr");

        addCell(row, getStudentId(student));
        addCell(row, student.firstName);
        addCell(row, student.lastName);
        addCell(row, student.email);
        addCell(row, student.phone);
        addCell(row, formatDate(student.dateOfBirth));
        addCell(row, student.gender);
        addCell(row, student.course);
        addCell(row, formatDate(student.admissionDate));
        addCell(row, student.marks);

        const actionCell = addCell(row, "");
        const actions = document.createElement("div");

        actions.className = "row-actions";

        // EDIT BUTTON
        const editBtn = document.createElement("button");

        editBtn.type = "button";
        editBtn.textContent = "Edit";
        editBtn.className = "secondary";

        editBtn.addEventListener("click", () => {
            editStudent(student);
        });

        // DELETE BUTTON
        const deleteBtn = document.createElement("button");

        deleteBtn.type = "button";
        deleteBtn.textContent = "Delete";
        deleteBtn.className = "danger";

        deleteBtn.addEventListener("click", () => {
            deleteStudent(getStudentId(student));
        });

        actions.append(editBtn, deleteBtn);
        actionCell.appendChild(actions);
        tableBody.appendChild(row);
    });
}

// =====================================================
// GET FORM DATA
// =====================================================
function getStudentFormData() {
    const marksValue = marksInput.value.trim();

    return {
        firstName: firstNameInput.value.trim(),
        lastName: lastNameInput.value.trim(),
        email: emailInput.value.trim(),
        phone: phoneInput.value.trim() || null,
        dateOfBirth: dateOfBirthInput.value || null,
        gender: genderInput.value || null,
        course: courseInput.value.trim() || null,
        admissionDate: admissionDateInput.value || null,
        marks: marksValue === "" ? null : Number(marksValue)
    };
}

// =====================================================
// VALIDATE STUDENT DATA
// =====================================================
function validateStudent(student) {
    if (
        !student.firstName ||
        !student.lastName ||
        !student.email
    ) {
        return "First name, last name, and email are required.";
    }

    if (
        student.firstName.length > 50 ||
        student.lastName.length > 50
    ) {
        return "First and last names cannot exceed 50 characters.";
    }

    if (student.email.length > 100) {
        return "Email cannot exceed 100 characters.";
    }

    if (student.phone && student.phone.length > 15) {
        return "Phone cannot exceed 15 characters.";
    }

    if (student.gender && student.gender.length > 10) {
        return "Gender cannot exceed 10 characters.";
    }

    if (
        student.marks !== null &&
        (
            !Number.isFinite(student.marks) ||
            student.marks < 0 ||
            student.marks > 100
        )
    ) {
        return "Marks must be between 0 and 100.";
    }

    return null;
}

// =====================================================
// CREATE AND UPDATE STUDENT
//
// CREATE:
// POST /api/students/addNew
//
// UPDATE:
// PUT /api/students/{studentId}
// =====================================================
form.addEventListener("submit", async event => {
    event.preventDefault();

    const id = studentIdInput.value.trim();
    const student = getStudentFormData();

    const validationError = validateStudent(student);

    if (validationError) {
        showMessage(validationError, true);
        return;
    }

    submitBtn.disabled = true;

    try {
        // Select endpoint and HTTP method.
        const url = id
            ? `${API_URL}/${encodeURIComponent(id)}`
            : `${API_URL}/addNew`;

        const method = id ? "PUT" : "POST";

        // Send request to Spring Boot.
        await apiRequest(url, {
            method,
            body: JSON.stringify(student)
        });

        // Reset the form and reload table.
        resetForm();
        await loadStudents();

        showMessage(
            id
                ? "Student updated successfully."
                : "Student added successfully."
        );
    } catch (error) {
        showMessage(
            error.message || "Unable to save student.",
            true
        );
    } finally {
        submitBtn.disabled = false;
    }
});

// =====================================================
// EDIT STUDENT
// =====================================================
function editStudent(student) {
    const id = getStudentId(student);

    if (id == null) {
        showMessage(
            "Student ID was not found in the API response.",
            true
        );
        return;
    }

    studentIdInput.value = id;

    firstNameInput.value = student.firstName ?? "";
    lastNameInput.value = student.lastName ?? "";
    emailInput.value = student.email ?? "";
    phoneInput.value = student.phone ?? "";
    dateOfBirthInput.value = student.dateOfBirth ?? "";
    genderInput.value = student.gender ?? "";
    courseInput.value = student.course ?? "";
    admissionDateInput.value = student.admissionDate ?? "";
    marksInput.value = student.marks ?? "";

    formTitle.textContent = "Edit Student";
    submitBtn.textContent = "Update Student";
    cancelBtn.classList.remove("hidden");

    showMessage("");

    form.scrollIntoView({
        behavior: "smooth",
        block: "start"
    });

    firstNameInput.focus();
}

// =====================================================
// DELETE STUDENT
//
// DELETE /api/students/{studentId}
//
// Requires a matching @DeleteMapping in the controller.
// =====================================================
async function deleteStudent(id) {
    if (id == null) {
        showMessage("Student ID is missing.", true);
        return;
    }

    if (
        !window.confirm(
            "Are you sure you want to delete this student?"
        )
    ) {
        return;
    }

    try {
        await apiRequest(
            `${API_URL}/${encodeURIComponent(id)}`,
            { method: "DELETE" }
        );

        if (String(studentIdInput.value) === String(id)) {
            resetForm();
        }

        await loadStudents();

        showMessage("Student deleted successfully.");
    } catch (error) {
        showMessage(
            error.message || "Unable to delete student.",
            true
        );
    }
}

// =====================================================
// RESET FORM
// =====================================================
function resetForm() {
    form.reset();
    studentIdInput.value = "";

    formTitle.textContent = "Add New Student";
    submitBtn.textContent = "Add Student";
    cancelBtn.classList.add("hidden");
}

// =====================================================
// CANCEL EDIT
// =====================================================
cancelBtn.addEventListener("click", () => {
    resetForm();
    showMessage("");
});

// =====================================================
// REFRESH STUDENT TABLE
// =====================================================
refreshBtn.addEventListener("click", loadStudents);

// =====================================================
// INITIAL LOAD
// =====================================================
loadStudents();
