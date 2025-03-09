# Use Node.js for building the frontend
FROM node:18 AS build
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json ./
RUN npm install

# Copy the rest of the frontend files
COPY . ./

# Build the React app
RUN npm run build

# Use Nginx to serve the built frontend
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]