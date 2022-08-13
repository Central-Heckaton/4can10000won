import React from 'react';
import Footer from './../components/Footer/Footer';
import BeerTypeFilter from './../components/beerTypeFilter/beerTypeFilter';

const BeerType = (props) => {
    return (
        <div>
            <BeerTypeFilter />
            <Footer />
        </div>
    );
};

export default BeerType;