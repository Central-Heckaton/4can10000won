// Rate.jsx
import React from 'react';
import RateStars from '../components/rateStars/rateStars';
import Nav from "../components/Nav/Nav";
import { useLocation } from 'react-router-dom';
import { useNavigate } from "react-router-dom";

const Rate = (props) => {
    const location = useLocation();
    const id = location.state.id;
    console.log(id);
    let navigate = useNavigate();

    return (
        <div>
            <Nav navigate={navigate} />
            <RateStars id={id}/>
        </div>
    );
};

export default Rate;