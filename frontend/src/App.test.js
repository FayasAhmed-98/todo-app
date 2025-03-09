import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import axios from "axios";
import App from "./App";

// Mock Axios
jest.mock("axios");

describe("Todo Task App", () => {
  beforeEach(() => {
    jest.clearAllMocks(); // Reset mocks before each test
  });

  test("renders the task form", () => {
    render(<App />);
    expect(screen.getByText("Add a Task")).toBeInTheDocument();
    expect(screen.getByPlaceholderText("Title")).toBeInTheDocument();
  });

  test("fetches and displays tasks", async () => {
    axios.get.mockResolvedValue({
      data: [
        { id: 1, title: "Task 1", description: "Description 1" },
        { id: 2, title: "Task 2", description: "Description 2" },
      ],
    });

    render(<App />);

    await waitFor(() => {
      expect(screen.getByText("Task 1")).toBeInTheDocument();
      expect(screen.getByText("Task 2")).toBeInTheDocument();
    });
  });

  test("adds a new task", async () => {
    axios.get.mockResolvedValue({ data: [] }); // Empty initial tasks
    axios.post.mockResolvedValue({
      data: { id: 3, title: "New Task", description: "New Description" },
    });

    render(<App />);

    fireEvent.change(screen.getByPlaceholderText("Title"), {
      target: { value: "New Task" },
    });
    fireEvent.change(screen.getByPlaceholderText("Description"), {
      target: { value: "New Description" },
    });
    fireEvent.click(screen.getByText("Add"));

    await waitFor(() => {
      expect(screen.getByText("New Task")).toBeInTheDocument();
      expect(screen.getByText("New Description")).toBeInTheDocument();
    });
  });

  test("marks a task as done", async () => {
    axios.get.mockResolvedValueOnce({
      data: [{ id: 1, title: "Task 1", description: "Description 1" }],
    });

    axios.put.mockResolvedValueOnce({}); // Mock API update call

    render(<App />);

    await waitFor(() => expect(screen.getByText("Task 1")).toBeInTheDocument());

    fireEvent.click(screen.getByText("Done"));

    expect(axios.put).toHaveBeenCalledWith(
        "http://localhost:8080/api/tasks/1/complete"
    );
  });
});
