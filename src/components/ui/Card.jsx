import classes from './Card.module.css';
import React from 'react';
function Card(props) {
  return <div className={classes.card}>{props.children}</div>;
}

export default Card;