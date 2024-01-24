FROM node:20.7.0-alpine as builder

ARG VITE_OKTA_CLIENT_ID
ARG VITE_OKTA_ISSUER
ARG VITE_REDIRECT_URI
ARG VITE_API_ADDRESS

ENV VITE_OKTA_CLIENT_ID=$VITE_OKTA_CLIENT_ID
ENV VITE_OKTA_ISSUER=$VITE_OKTA_ISSUER
ENV VITE_REDIRECT_URI=$VITE_REDIRECT_URI
ENV VITE_API_ADDRESS=$VITE_API_ADDRESS

LABEL authors="Misiac"
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . ./
RUN npm run build

FROM nginx:1.25.3-alpine
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 80

CMD ["nginx","-g","daemon off;"]