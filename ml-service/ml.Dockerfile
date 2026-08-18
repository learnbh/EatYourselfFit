# =========================================================================
# STAGE 1: BUILDER
# Downloads and installs Python dependencies in an isolated build layer.
# =========================================================================
FROM ghcr.io/astral-sh/uv:python3.11-bookworm-slim AS builder

# Set working directory inside the builder container
WORKDIR /app

# Enable bytecode compilation to speed up startup times
ENV UV_COMPILE_BYTECODE=1

# Copy dependency definition files first to maximize Docker layer caching
COPY pyproject.toml uv.lock ./
COPY src ./src

# Strictly install production dependencies into a virtual environment (.venv)
RUN uv sync --frozen --no-dev

# =========================================================================
# STAGE 2: FINAL RUNTIME
# Copies only the pre-built dependencies and source code into the final image.
# =========================================================================
FROM ghcr.io/astral-sh/uv:python3.11-bookworm-slim AS final

# Set working directory for the application
WORKDIR /app

# Ensure Python outputs logs directly to stdout/stderr without buffering
ENV PYTHONUNBUFFERED=1

# Copy the pre-built virtual environment from the builder stage
COPY --from=builder /app/.venv /app/.venv

# Ensure the virtual environment's executables are prioritized in PATH
ENV PATH="/app/.venv/bin:$PATH"

# Copy application source code into the runtime container (respects .dockerignore)
COPY src ./src

# Run as an unprivileged user for defense-in-depth
RUN useradd --create-home --uid 1000 appuser \
    && chown -R appuser:appuser /app
USER appuser

# Expose standard application port for FastAPI
EXPOSE 8000

# Default command runs the FastAPI application via uvicorn
CMD ["uvicorn", "src.app.main:app", "--host", "0.0.0.0", "--port", "8000"]
