const { createProxyMiddleware } = require('http-proxy-middleware');

// "/api" 경로가 시작하면 프록시 미들웨어를 실행한다.
module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
//       target: 'http://54.167.58.203:8080',
      target: 'http://localhost:8080',
      changeOrigin: true
    })
  );
};