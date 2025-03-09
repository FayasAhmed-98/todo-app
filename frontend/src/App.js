import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/tasks");
      setTasks(response.data.slice(-5)); // Show only last 5 tasks
    } catch (error) {
      console.error("Error fetching tasks:", error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!title || !description) return;

    try {
      const response = await axios.post("http://localhost:8080/api/tasks", {
        title,
        description,
      });

      setTitle("");
      setDescription("");
      setTasks((prevTasks) => [...prevTasks.slice(-4), response.data]); // Keep only last 5
    } catch (error) {
      console.error("Error adding task:", error);
    }
  };

  const markCompleted = async (id) => {
    try {
      await axios.put(`http://localhost:8080/api/tasks/${id}/complete`);

      // Fetch the latest tasks to update the UI instantly
      fetchTasks();
    } catch (error) {
      console.error("Error marking task as complete:", error);
    }
  };

  return (
      <div className="container">
        <div className="task-form">
          <h2>Add a Task</h2>
          <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
            />
            <textarea
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                required
            />
            <button type="submit">Add</button>
          </form>
        </div>

        <div className="task-list">
          {tasks.map((task) => (
              <div key={task.id} className="task-card">
                <div>
                  <h3>{task.title}</h3>
                  <p>{task.description}</p>
                </div>
                <button className="done-btn" onClick={() => markCompleted(task.id)}>
                  Done
                </button>
              </div>
          ))}
        </div>
      </div>
  );
}

export default App;
