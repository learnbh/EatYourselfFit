import {useEffect, useState} from "react";
import axios from "axios";
import {type Params, useParams} from "react-router-dom";
import type {Ingredient} from "../types.ts";

export default function IngredientDetails(){
    const ingedientId:Readonly<Params<string>> = useParams()
    const [ingredient, setIngredient] = useState<Ingredient>()

    useEffect(() => {
        axios.get("/eyf/ingredients/"+ingedientId.id)
            .then(response => setIngredient(response.data))
            .catch(e=> console.log(e.message()));
    }, [ingedientId]);

    return(
        <>
            <div>
                {ingredient?ingredient.product:"Fehler Zutat nicht gefunden!"}
            </div>
        </>
    );
}