import React, { useContext, useState } from "react";
import HomePopup from "../components/home/HomePopup";
import ModelService from "../services/ModelService";
import Backdrop from "../components/ui/Backdrop";
import { ModelContext } from "../store/model-context";
import HomeScreen from "../components/home/HomeScreen";
function Home(props) {
  const [isLoading, setIsLoading] = useState();
  const [wasError, setWasError] = useState(false);
  const model = useContext(ModelContext);

  function createModelHandler(leagueIdData) {
    setIsLoading(true);
    const prev = localStorage.getItem("leagueId")
    ModelService.createModel(leagueIdData)
      .then((res) => {
        setIsLoading(false);
        model.setModelLoaded(true);
        setWasError(false);
      })
      .catch(function (error) {
        localStorage.setItem("leagueId", prev)
        if (localStorage.getItem("leagueId") === 'null') {
          localStorage.removeItem("leagueId")
        }
        setWasError(true);
        setIsLoading(false);
      });
  }

  function createDemoHandler() {
    setIsLoading(true);
    ModelService.createDemo()
      .then((res) => {
        setIsLoading(false);
        model.setModelLoaded(true);
        setWasError(false);
      })
      .catch(function (error) {
        setWasError(true);
        setIsLoading(false);
      });
  }

  function popupHandler() {
    model.setModelLoaded(false);
  }

  if (!model.isLoaded) {
    return (
      <Backdrop>
        <HomePopup
          loading={isLoading}
          error={wasError}
          createModel={createModelHandler}
          createDemo={createDemoHandler}
        />
      </Backdrop>
    );
  }
  return (
    <div>
      <HomeScreen handlePopup={popupHandler} />
    </div>
  );
}

export default Home;
