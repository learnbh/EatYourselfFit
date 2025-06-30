import {useCallback, useEffect, useState} from "react";
import axios from "axios";
import type {RecipeType} from "../types.ts";
import RecipeItem from "../component/RecipeItem.tsx";
import meatskewer from "../assets/recipe/meat-skewer_160x90.jpg";

export default function Recipe(){
    const [recipes, setRecipes] = useState<RecipeType[]>([])
    //const [isLoading, setIsLoading] = useState<boolean>(false);

    const getDBData = useCallback(async () => {
        try {
            //setIsLoading(true);
            const response = await axios.get("/eyf/recipe");
            setRecipes(response.data);
        } catch (error){
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        }
        // finally {
        //     setIsLoading(false);
        // }
    }, []);

    useEffect(() => {
        (async () => {
            await getDBData();
        })();
    }, [getDBData]);

    return (
        <>
            <div className="flex flex-col gap-2">
                <h1>Rezepte</h1>
                <div className="flex flex-row flex-wrap overflow-auto gap-5 justify-center">
                    { recipes && (
                        recipes.map(recipe =>
                            <RecipeItem
                                key = {recipe.id}
                                recipe={recipe}
                                img={meatskewer}
                                imgAlt="picture shows a meat skewer dish from pixabay.com"
                                imgWidth="100%"
                                imgHeight="100%"
                            />
                        )
                    ) }
                </div>
            </div>
        </>
    );
}