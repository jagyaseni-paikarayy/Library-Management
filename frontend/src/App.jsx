import { useEffect, useMemo, useState } from 'react';
import './App.css';

const API_URL = '/api/courses';

function App() {
const [courses, setCourses] = useState([]);
const [form, setForm] = useState({
id: '',
name: '',
instructor: '',
});
const [search, setSearch] = useState('');
const [loading, setLoading] = useState(true);
const [error, setError] = useState('');
const [message, setMessage] = useState('');

async function loadCourses() {
setLoading(true);
setError('');

try {
  const response = await fetch(API_URL);

  if (!response.ok) {
    throw new Error(`Unable to load courses (${response.status})`);
  }

  const data = await response.json();
  setCourses(data);
} catch (err) {
  setError(err.message || 'Unable to connect to the server.');
} finally {
  setLoading(false);
}

}

useEffect(() => {
loadCourses();
}, []);

const filteredCourses = useMemo(() => {
const term = search.toLowerCase();

return courses.filter((course) =>
  String(course.id).toLowerCase().includes(term) ||
  course.name.toLowerCase().includes(term) ||
  course.instructor.toLowerCase().includes(term)
);

}, [courses, search]);

async function handleAddCourse(event) {
event.preventDefault();
setError('');
setMessage('');

try {
  const response = await fetch(API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      id: Number(form.id),
      name: form.name.trim(),
      instructor: form.instructor.trim(),
    }),
  });

  if (!response.ok) {
    throw new Error(`Unable to add course (${response.status})`);
  }

  setForm({ id: '', name: '', instructor: '' });
  setMessage('Course added successfully!');
  await loadCourses();
} catch (err) {
  setError(err.message || 'Unable to add course.');
}

}

async function handleDeleteCourse(id) {
if (!window.confirm("Delete course ${id}?")) return;

setError('');
setMessage('');

try {
  const response = await fetch(`${API_URL}/${id}`, {
    method: 'DELETE',
  });

  if (!response.ok) {
    throw new Error(`Unable to delete course (${response.status})`);
  }

  setMessage('Course deleted successfully!');
  await loadCourses();
} catch (err) {
  setError(err.message || 'Unable to delete course.');
}

}

return (
<div className="app-shell">
<aside className="sidebar">
<div className="brand">
<span className="brand-icon">L</span>
<span>Libra<span className="brand-light">ry</span></span>
</div>

    <p className="nav-label">MAIN MENU</p>
    <div className="nav-item active">▦ &nbsp; Dashboard</div>
    <div className="nav-item">▤ &nbsp; Course Management</div>

    <div className="sidebar-bottom">
      <div className="avatar">LM</div>
      <div>
        <strong>Library Admin</strong>
        <small>Management Portal</small>
      </div>
    </div>
  </aside>

  <main className="main-content">
    <header className="topbar">
      <div>
        <p className="eyebrow">LIBRARY MANAGEMENT SYSTEM</p>
        <h1>Dashboard</h1>
      </div>
      <span className="status-pill">● Local Environment</span>
    </header>

    <section className="welcome">
      <div>
        <p className="eyebrow">WELCOME BACK</p>
        <h2>Manage your courses with ease.</h2>
        <p>Organize courses, track instructors, and manage your records.</p>
      </div>
      <div className="welcome-icon">📚</div>
    </section>

    <section className="stats-grid">
      <div className="stat-card">
        <span>Total Courses</span>
        <strong>{courses.length}</strong>
        <small>Courses in your library</small>
      </div>
      <div className="stat-card">
        <span>Search Results</span>
        <strong>{filteredCourses.length}</strong>
        <small>Matching your search</small>
      </div>
    </section>

    <section className="content-grid">
      <div className="panel">
        <div className="panel-heading">
          <div>
            <h2>Add a Course</h2>
            <p>Enter the course details below.</p>
          </div>
          <span className="panel-icon">＋</span>
        </div>

        <form onSubmit={handleAddCourse}>
          <label htmlFor="courseId">Course ID</label>
          <input
            id="courseId"
            type="number"
            min="1"
            required
            value={form.id}
            onChange={(e) =>
              setForm({ ...form, id: e.target.value })
            }
            placeholder="Enter a unique ID"
          />

          <label htmlFor="courseName">Course Name</label>
          <input
            id="courseName"
            required
            maxLength="100"
            value={form.name}
            onChange={(e) =>
              setForm({ ...form, name: e.target.value })
            }
            placeholder="e.g. Java Programming"
          />

          <label htmlFor="instructor">Instructor</label>
          <input
            id="instructor"
            required
            maxLength="100"
            value={form.instructor}
            onChange={(e) =>
              setForm({ ...form, instructor: e.target.value })
            }
            placeholder="Enter instructor name"
          />

          <button className="primary-button" type="submit">
            + Add Course
          </button>
        </form>
      </div>

      <div className="panel course-panel">
        <div className="panel-heading">
          <div>
            <h2>Course Directory</h2>
            <p>View and manage available courses.</p>
          </div>
          <span className="course-count">{courses.length} total</span>
        </div>

        <input
          className="search-input"
          type="search"
          aria-label="Search courses"
          placeholder="Search by ID, name, or instructor..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />

        {error && <p className="alert error">{error}</p>}
        {message && <p className="alert success">{message}</p>}

        {loading ? (
          <p className="empty-state">Loading courses...</p>
        ) : filteredCourses.length === 0 ? (
          <p className="empty-state">
            No courses found. Add a course or change your search.
          </p>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Course</th>
                  <th>Instructor</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {filteredCourses.map((course) => (
                  <tr key={course.id}>
                    <td>{course.id}</td>
                    <td>{course.name}</td>
                    <td>{course.instructor}</td>
                    <td>
                      <button
                        className="delete-button"
                        type="button"
                        onClick={() => handleDeleteCourse(course.id)}
                        aria-label={`Delete ${course.name}`}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </section>

    <footer>
      Library Management System · React + Spring Boot
    </footer>
  </main>
</div>

);
}

export default App;