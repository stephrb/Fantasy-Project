import React , { useContext, useState } from 'react';
import HomePopup from '../components/home/HomePopup';
import ModelService from '../services/ModelService';
import { useNavigate } from 'react-router-dom';
import Backdrop from '../components/home/Backdrop';
import classes from './Home.module.css';
import { ModelContext } from '../store/model-context'
function Home(props) {
    const [isLoading, setIsLoading] = useState();
    const [wasError, setWasError] = useState(false);
    const model = useContext(ModelContext);
    
    function createModelHandler(leagueIdData) {
        setIsLoading(true);
        ModelService.createModel(leagueIdData)
        .then( (res) => {
            setIsLoading(false);
            model.setModelLoaded(true);
            setWasError(false);
        }).catch(function (error) {
            setWasError(true);
            setIsLoading(false);
        });
    }

    function popupHandler() {
        model.setModelLoaded(false);
    }

    if (!model.isLoaded) {
        return (
            <Backdrop > 
                    <h1 className={classes.format}> </h1>
                    <HomePopup loading={isLoading} error={wasError} createModel={createModelHandler}/>
            </Backdrop>
        );
    }
    return (
        <div>
            <button onClick = {popupHandler}> Set new League? </button>
        </div>
    )
    
}

export default Home;