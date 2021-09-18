const express = require('express');
const cors = require('cors');
const morgan = require('morgan');
const mongoose = require('mongoose');

const bountiesRoutes = require('./routes/bountiesRoutes');

const app = express();

app.use(cors())
app.use(express.json())
app.use(morgan('dev'));
app.use('/api/bounties/', bountiesRoutes);
require('dotenv').config();

mongoose.connect(process.env.DB_URI, { useNewUrlParser: true, useUnifiedTopology: true })
    .then((result) => {
        console.log("DB Connected");
        app.listen(process.env.PORT || 3000);
    })
    .catch((e) => console.log(e));