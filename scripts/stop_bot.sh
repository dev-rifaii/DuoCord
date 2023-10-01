#!/bin/bash

# Check if any Java processes are running
java_pids=$(pgrep java)

if [ -n "$java_pids" ]; then
  # Stop all Java processes
  for pid in $java_pids; do
    kill -9 "$pid"
    echo "Java process stopped (PID: $pid)"
  done
else
  echo "No Java processes found"
fi