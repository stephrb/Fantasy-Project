import classes from './Card.module.css';
import React from 'react';
function Card(props) {
  const size = props.size + '%';
  return <div className={classes.card} style={{padding: size}}> {props.children}</div>;
}

export default Card;