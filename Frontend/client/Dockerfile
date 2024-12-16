# Build stage
FROM node:18-alpine AS build

# Set working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install --silent

# Copy the rest of the application files
COPY . .

# Build the app for production
RUN npm run build

# Production stage
FROM nginx:alpine

# Set the working directory
WORKDIR /usr/share/nginx/html

# Remove the default nginx index page
RUN rm -rf ./*

# Copy the build files from the build stage to the nginx directory
COPY --from=build /app/dist .

# Expose port 80 to access the app
EXPOSE 80

# Start nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
