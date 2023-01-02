import React, { useState } from 'react';
import Card from '../ui/Card';
import classes from './Streaming.module.css'
function Streaming(props) {
    const [average, setAverage] = useState(25);
    const [std, setStd] = useState(15);

    return (
        <div style={{marginTop:'1rem'}}>
            <Card size='10'>
                <p className={classes.default} style={{backgroundColor:"rgb(44, 42, 41)", fontSize:'medium', border:"rgb(44, 42, 41)"}}>Streaming Spots</p>
                <div style={{display:"flex", flexDirection:"column"}}>
                    <label className={classes.default}>Average: <span style={{color:'#abdaed'}}>{average}</span></label>
                    <input 
                        type="range"
                        min={0}
                        max={50}
                        step=".5"
                        value={average}
                        onChange={(event) => setAverage(Number(event.target.value))}
                    />
                </div>

                <div style={{display:"flex", flexDirection:"column"}}>
                    <label className={classes.default}>Standard Deviation: <span style={{color:'#abdaed'}}>{std}</span></label>
                    <input 
                        type="range"
                        min={0}
                        max={30}
                        step=".5"
                        value={std}
                        onChange={(event) => setStd(Number(event.target.value))}
                    />
                </div>
                <div style={{margin:'.5rem'}}>
                    {props.streamers < 3 && 
                    <button style={{marginRight:'.5rem'}} className={classes.actionsbutton} onClick={() => {props.addStreamerHandler(average, std)}}>
                        Add
                    </button>}
                    {props.streamers > 0 && 
                    <button style={{ border: "#ca5850", backgroundColor: "#ca5850"}} className={classes.actionsbutton} onClick={() => {props.deleteStreamerHandler()}}>
                        Remove
                    </button>}
                </div>
               
                
            </Card>
        </div>

    );
}

export default Streaming;