import React from "react";
import classes from './Streaming.module.css'
function Streaming() {
  const [mean, setMean] = React.useState("");
  const [numGames, setNumGames] = React.useState("");
  const [stdDev, setStdDev] = React.useState("");

  function handleSubmit(event) {
    event.preventDefault();
    // Add code to handle form submission here
  }

  return (
    <form onSubmit={handleSubmit} className={classes.form}>
      <h2>Streaming</h2>
      <div className={classes.form_row}>
        <label className={classes.form_label}>Mean:</label>
        <input type="text" value={mean} onChange={event => setMean(event.target.value)} />
      </div>
      <div className={classes.form_row}>
        <label className={classes.form_label}>Number of games:</label>
        <input type="text" value={numGames} onChange={event => setNumGames(event.target.value)} />
      </div>
      <div className={classes.form_row}>
        <label className={classes.form_label}>Standard deviation:</label>
        <input type="text" value={stdDev} onChange={event => setStdDev(event.target.value)} />
      </div>
      <button type="submit" className={classes.form_button}>Submit</button>
    </form>
  );
}
export default Streaming