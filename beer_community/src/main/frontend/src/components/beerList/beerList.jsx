import React from 'react';
import BeerBox from '../beerBox/beerBox';

const BeerList = (props) => {
    return (
        <div>
            <BeerBox />
            <BeerBox />
            <BeerBox />
        </div>
    );
};

export default BeerList;