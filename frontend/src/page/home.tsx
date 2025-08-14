import Tile from "../component/Tile.tsx";
import IngredientSearch from "../component/IngredientSearch.tsx";
import breakfast from "../assets/breakfast640x427.jpg";
import cookAsia from "../assets/woman-cook-asia640x427.jpg";
import mealInOven from "../assets/mealInOven640x427.jpg";
import shoppingList from "../assets/shopping-list640x427.jpg";
import { GiCook } from "react-icons/gi";
import { GiHotMeal } from "react-icons/gi";
import { GrScheduleNew } from "react-icons/gr";
import { GrSchedule } from "react-icons/gr";
import {useCallback, useState} from "react";
import type {Ingredient} from "../types.ts";
import axios from "axios";
import IngredientNotFound from "../component/IngredientNotFound.tsx";
import {useNavigate} from "react-router-dom";
import IngredientSearchResultItem from "../component/IngredientSearchResultItem.tsx";

export default function Home(){
    const [ingredientSearch, setIngredientSearch] = useState<string>("");
    const [ingredientsSearch, setIngredientsSearch] = useState<Ingredient[]>([]);
    const [ingredientNotFoundVisible, setIngredientNotFoundVisible] = useState(false);
    const [isLoading, setIsLoading] = useState<boolean>(false);

    const routeTo =useNavigate()

    function abortAddIngredient (){
        setIngredientSearch("");
        setIngredientsSearch([]);
    }

    const editIngredientGeneratedByOpenAi = useCallback(async (search:string) => {
        if(search.trim() === ""){
            setIngredientsSearch([]);
            setIsLoading(false);
            setIngredientNotFoundVisible(false);
            return;
        }
        try {
            setIsLoading(true);
            const response = await axios.get("/eyf/ingredients/name/" + search);
            if (response.data.length > 0) {
                const ingredientId:string = response.data[0].id;
                setIngredientsSearch(response.data);
                sessionStorage.setItem('detailId',ingredientId);
                window.open("/ingredient", "_blank");
                setIngredientNotFoundVisible(false);
            } else {
                setIngredientsSearch([]);
                setIngredientNotFoundVisible(true);
            }
        } catch (error){
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        }finally {
            setIsLoading(false);
        }
    }, []);

    const addPerOpenAiIngredient = async () => {
        try {
            setIngredientNotFoundVisible(false);
            setIsLoading(true);
            await axios.post("/eyf/ingredients/openai/add", {product: ingredientSearch, variation:""})
            await editIngredientGeneratedByOpenAi(ingredientSearch);
        } catch (error){
            if (axios.isAxiosError(error)) {
                if (error.response && error.response.data.message) {
                    console.error('Axios errors_:', error.response?.data || error.message);
                    alert(error.response.data.message);
                } else {
                    console.log("error: "+error);
                    alert('Es konnten keine Nährstoffe gefunden werden. Änderne die Anfrage und versuche es erneut.');
                }
            } else {
                console.log(error);
            }
        }finally {
            setIsLoading(false);
        }
    }
    function addPerUser(){
        routeTo("/ingredient/add/"+ingredientSearch);
    }

    return(
        <>
            <div className="flex flex-col">
                <div className="flex flex-col">
                    <IngredientSearch
                        placeholder="Zutat suchen"
                        name="addingredient"
                        setSearchResult={setIngredientsSearch}
                        setSearchNotFoundVisible={setIngredientNotFoundVisible}
                        setIsLoading={setIsLoading}
                    />
                </div>
                <div className="space-y-4">
                    {ingredientNotFoundVisible && (
                        <IngredientNotFound
                            addPerAi={addPerOpenAiIngredient}
                            addPerUser={addPerUser}
                            abort={abortAddIngredient}
                        />
                    )}
                </div>
                <div className={ingredientsSearch.length > 0 ? "flex flex-wrap gap-4 border sm:pb-2 sm:pt-2 justify-center":""}>
                    {ingredientsSearch.length > 0 && (
                        ingredientsSearch.map((i:Ingredient) => <IngredientSearchResultItem
                                key = {i.id}
                                ingredient={i}
                                children={null}
                            />
                        )
                    )}
                </div>
                    {isLoading && (
                        <span>Daten werden geladen ...</span>
                    )}
            <div className="home pt-2">
                <Tile to="/recipe"
                      title="Rezepte"
                      element ={<GiCook className="m-1"  style = {{ width: "inherit", height:"inherit"}} />}
                      img={mealInOven}
                      imgAlt="picture shows a chicken dish from pixabay.com"
                      imgWidth="100%"
                      imgHeight="200"
                />
                <Tile to="/weekplan"
                      title="Wochenplaner"
                      element = {<GrSchedule className=" m-1"  style = {{ width: "inherit", height:"inherit"}} />}
                      img={breakfast}
                      imgAlt="picture shows a Table with breakfast made by palacioerick, pixabay.com"
                      imgWidth="100%"
                      imgHeight="200"
                />
                <Tile to="/recipeplan"
                      title="Rezepte erstellen"
                      element = {<GiHotMeal className="m-1"  style = {{ width: "inherit", height:"inherit"}} />}
                      img={cookAsia}
                      imgAlt="picture shows a woman cooking asian food from pixabay.com"
                      imgWidth="100%"
                      imgHeight="200"
                />
                <Tile to="/shoppinglist"
                      title="Einkaufsliste"
                      element = {<GrScheduleNew className="m-1" style = {{ width: "inherit", height:"inherit"}} />}
                      img={shoppingList}
                      imgAlt="picture shows someone writing a shoppinglist, pixabay.com"
                      imgWidth="100%"
                      imgHeight="200"
                />
            </div>
        </div>
        </>
    )
}