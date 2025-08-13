import Tile from "../component/Tile.tsx";
import breakfast from "../assets/breakfast640x427.jpg";
import cookAsia from "../assets/woman-cook-asia640x427.jpg";
import mealInOven from "../assets/mealInOven640x427.jpg";
import shoppingList from "../assets/shopping-list640x427.jpg";
import { GiCook } from "react-icons/gi";
import { GiHotMeal } from "react-icons/gi";
import { GrScheduleNew } from "react-icons/gr";
import { GrSchedule } from "react-icons/gr";
import {type ChangeEvent, useState} from "react";

export default function Home(){
    const [ingredientSearch, setIngredientSearch] = useState<string>("");

    function handleChangeIngredient(e:ChangeEvent<HTMLInputElement>){
        e.preventDefault();
        setIngredientSearch( e.target.value)
    }
    return(
        <>
            <div className="flex flex-col">
                <div className="flex flex-col">
                    <input
                        placeholder="Zutat suchen"
                        value={ingredientSearch}
                        name="addingredient"
                        id="addingredient"
                        onChange={handleChangeIngredient}
                    />
                </div>
            <div className="home">
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