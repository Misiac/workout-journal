FROM node:20.7.0-alpine as builder

ARG REACT_APP_OKTA_CLIENT_ID
ARG REACT_APP_OKTA_ISSUER
ARG REACT_APP_REDIRECT_URI

ENV REACT_APP_OKTA_CLIENT_ID=$REACT_APP_OKTA_CLIENT_ID
ENV REACT_APP_OKTA_ISSUER=$REACT_APP_OKTA_ISSUER
ENV REACT_APP_REDIRECT_URI=$REACT_APP_REDIRECT_URI

LABEL authors="Misiac"
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . ./
RUN npm run build

FROM nginx:1.25.3-alpine
COPY --from=builder /app/build /usr/share/nginx/html
EXPOSE 80

CMD ["nginx","-g","daemon off;"]