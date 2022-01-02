import React from 'react';
import classes from './Header.module.css';
function Header(props) {
    const text = props.text;
    return (
        <h1 className={classes.header}>{text}</h1>
    );
}

export default Header;