GRANT CONNECT ON DATABASE PROJECT_NAME_db TO PROJECT_NAME;
GRANT USAGE ON SCHEMA public TO PROJECT_NAME;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO PROJECT_NAME;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO PROJECT_NAME;

CREATE EXTENSION IF NOT EXISTS pgcrypto;