import React, { createContext, useState } from 'react';

export const ModelContext = createContext();

export const ModelProvider = ({ children }) => {
    const [isLoaded, setModelLoaded] =  useState(false);

    return (
        <ModelContext.Provider
            value = {{isLoaded, setModelLoaded}}>
            {children}
            </ModelContext.Provider>
    );
};
    