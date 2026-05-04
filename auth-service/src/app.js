const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const authRoutes = require('./routes/auth');

const app = express();
const PORT = process.env.PORT || 3001;

app.use(cors());
app.use(express.json());

mongoose.connect(process.env.MONGO_URI || 'mongodb://localhost:27017/authdb')
  .then(() => console.log('Connected to MongoDB'))
  .catch(err => console.error('MongoDB connection error:', err));

app.use('/auth', authRoutes);

app.get('/', (req, res) => {
  res.json({ message: 'Auth Service is running!' });
});

app.listen(PORT, () => {
  console.log(`Auth Service running on port ${PORT}`);
});
