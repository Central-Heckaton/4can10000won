// Rate.jsx
import React from 'react';
import RateStars from '../components/rateStars/rateStars';
import { useLocation } from 'react-router-dom';

const Rate = (props) => {
    const location = useLocation();
    const id = location.state.id;
    console.log(location);

    return (
        <div>
            <RateStars
                id={id}/>
        </div>
    );
};

export default Rate;