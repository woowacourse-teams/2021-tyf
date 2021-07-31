import express from 'express';
import path from 'path';

const app = express();
const PORT = 9000;

app.use('/', express.static(path.resolve('dist')));

app.get('/*', (req, res) => {
  res.sendFile(path.resolve('dist', 'index.html'));
});

app.listen(PORT, () => {
  console.log(`serve on port ${PORT}`);
});
