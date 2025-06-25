import {useCallback, useEffect, useState} from "react";
import axios from "axios";
import type {RecipeType} from "../types.ts";

export default function RecipeShow(){
    const recipeId: string | null = sessionStorage.getItem("detailId");
    const [isLoading, setLoading] = useState<boolean>(false)
    const [recipe, setRecipe] = useState<RecipeType>()

    const getDetails = useCallback( async ( id: string | null ) => {
        try {
            if (id) {
                setLoading(true)
                const responseIngredient = await axios.get("/eyf/recipe/" + id)
                const recipe: RecipeType = responseIngredient.data
                setRecipe(recipe)
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        } finally {
            setLoading(false)
        }
    }, [])

    useEffect( () => {
        (async () => {
            await getDetails(recipeId);
        })();
    }, [recipeId, getDetails] );

    return(
        <>
            <div>
                {isLoading && (
                    <h1> Die Details des Rezeptes werden geladen</h1>
                )}
                <h1>{"recipeId: " + recipeId}</h1>
                {recipe && (
                     <h1>{recipe.title}</h1>
                )}
            </div>
        </>
    )
}